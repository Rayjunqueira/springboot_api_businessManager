package com.api.business_manager_api.Controllers;

import com.api.business_manager_api.Dtos.CustomerDto;
import com.api.business_manager_api.Dtos.OrderDto;
import com.api.business_manager_api.Mappers.OrderMapper;
import com.api.business_manager_api.Models.CustomerModel;
import com.api.business_manager_api.Models.OrderModel;
import com.api.business_manager_api.Models.ProductModel;
import com.api.business_manager_api.Services.CustomerService;
import com.api.business_manager_api.Services.OrderService;
import com.api.business_manager_api.Services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/order")
public class OrderController {

    final OrderService orderService;
    final OrderMapper orderMapper;
    final CustomerService customerService;
    final ProductService productService;
    public OrderController(OrderService orderService, OrderMapper orderMapper, CustomerService customerService, ProductService productService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.customerService = customerService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Object> saveOrder (@RequestBody @Valid OrderDto orderDto) {
        try {
            OrderModel orderModel = orderMapper.toOrderModel(orderDto);
            CustomerModel customerModel = customerService.findById(orderDto.getCustomer().getCustomer_id())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            orderModel.setCustomer(customerModel);
            for (ProductModel product : orderDto.getProducts()) {
                ProductModel productModel = productService.findById(product.getProduct_id())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                orderModel.getProducts().add(productModel);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(orderModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }
    @GetMapping
    public ResponseEntity<List<OrderModel>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneOrder(@PathVariable(value = "id") UUID id) {
        Optional<OrderModel> orderModelOptional = orderService.findById(id);
        if (!orderModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder (@PathVariable(value = "id") UUID id,
                                                  @RequestBody @Valid OrderDto orderDto) {
        Optional<OrderModel> orderModelOptional  = orderService.findById(id);
        if (!orderModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found!");
        }
        OrderModel orderModel = orderModelOptional.get();
        orderModel.setProducts(orderDto.getProducts().stream().map(t -> productService.findById(t.getProduct_id())
                .orElseThrow(() -> new RuntimeException("Product not found"))).collect(Collectors.toList()));
        orderModel.setCustomer(orderDto.getCustomer());
        orderModel.setTotalAmount(orderDto.getTotalAmount());

        return ResponseEntity.status(HttpStatus.OK).body(orderService.save(orderModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable(value = "id") UUID id) {
        Optional<OrderModel> orderModelOptional = orderService.findById(id);
        if (!orderModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found!");
        }
        orderService.delete(orderModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Order deleted succesfully!");
    }


}
