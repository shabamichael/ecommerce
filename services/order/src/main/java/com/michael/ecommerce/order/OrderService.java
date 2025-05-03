package com.michael.ecommerce.order;

import com.michael.ecommerce.customer.CustomerResponse;
import com.michael.ecommerce.exception.BusinessException;
import com.michael.ecommerce.kafka.OrderConfirmation;
import com.michael.ecommerce.kafka.OrderProducer;
import com.michael.ecommerce.payment.PaymentClient;
import com.michael.ecommerce.payment.PaymentRequest;
import com.michael.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.michael.ecommerce.customer.CustomerClient;
import com.michael.ecommerce.orderline.OrderLineRequest;
import com.michael.ecommerce.orderline.OrderLineService;
import com.michael.ecommerce.product.ProductClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(@Valid OrderRequest orderRequest) {
        // First check if the Customer exist --> From the Customer Microservice--> use OpenFeign to call the Customer Microservice
        var customer = this.customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: Customer not found "
                        + orderRequest.customerId()));

        // Purchase the products --> From the Product Microservice --> use (RestTemplate) to call the Product Microservice
        var purchasedProducts=this.productClient.purchaseProducts(orderRequest.products());

        // Persist the order object
        var order = this.orderRepository.save(mapper.toOrder(orderRequest));

        // Persist the order lines
        for (PurchaseRequest purchaseRequest : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(), // Assuming productId exists in PurchaseRequest
                            purchaseRequest.quantity()   // Assuming quantity exists in PurchaseRequest
                    )
            );
        }

        // Start payment process
        var paymentRequest = new PaymentRequest(
                orderRequest.amount(),
                orderRequest.paymentMethod(),
                order.getId(),
                orderRequest.reference(),
                new CustomerResponse(
                        customer.id(),
                        customer.firstName(),
                        customer.lastName(),
                        customer.email()
                )
        );
        paymentClient.requestOrderPayment(paymentRequest);



        // Send the confirmation to our notification microservice --> Notification-ms (Kafka broker)
        var confirmation = new OrderConfirmation(
                orderRequest.reference(),
                orderRequest.amount(),
                orderRequest.paymentMethod(),
                new CustomerResponse(
                        customer.id(),
                        customer.firstName(),
                        customer.lastName(),
                        customer.email()),
                purchasedProducts);

        orderProducer.sendOrderConfirmation(confirmation);

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream().map(mapper::fromOrder)
                .collect(Collectors.toList());

    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No order found with the provided ID: %d", orderId)));
    }
}