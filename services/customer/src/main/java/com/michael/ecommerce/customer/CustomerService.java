package com.michael.ecommerce.customer;

import com.michael.ecommerce.exception.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;


    public String createCustomer(@Valid CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId() ;
    }

    public void updateCustomer(@Valid CustomerRequest request) {
        var customer = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        String.format("Cannot update customer::No customer  found with id: %s", request.id());
        mergeCustomer(customer, request);
        repository.save(customer);
    }

    private void mergeCustomer(Customer customer,  CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName())){
            customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())){
            customer.setLastName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }
        if(request.address() != null){
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existById(String customerId) {
       return  repository.findById(customerId).isPresent();

    }

    public CustomerResponse findByCustomerId(String customerId) {
    return repository.findById(customerId).map(mapper::fromCustomer)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);
    }
}
