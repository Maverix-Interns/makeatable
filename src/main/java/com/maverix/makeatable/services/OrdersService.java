package com.maverix.makeatable.services;

import com.maverix.makeatable.dto.Orders.*;
import com.maverix.makeatable.models.Orders;
import com.maverix.makeatable.repositories.OrdersRepository;
import com.maverix.makeatable.repositories.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final RestaurantRepository restaurantRepository;

    public OrdersService(OrdersRepository ordersRepository, RestaurantRepository restaurantRepository) {
        this.ordersRepository = ordersRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<OrdersGetDto> getAllOrders() {
        List<Orders> ordersList = ordersRepository.findAll();
        return ordersList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public LastOrderDto getLastOrderForUser(Long userId){
        Optional<Orders> lastOrderOptional = ordersRepository.findTopByCreatedByUserIdOrderByDateTimeDesc(userId);
        if (lastOrderOptional.isPresent()) {
            Orders lastOrder = lastOrderOptional.get();


            String location = "";
            if (lastOrder.getRestaurant() != null) {
                location = lastOrder.getRestaurant().getLocation();
            }

            LastOrderDto lastOrderDto = new LastOrderDto();
            lastOrderDto.setId(lastOrder.getId());
            lastOrderDto.setLocation(location);
            lastOrderDto.setDateTime(lastOrder.getDateTime());
            lastOrderDto.setRoomType(lastOrder.getTypeRoom());

            return new OrderResponseDTO(lastOrderDto).getLastOrder();
        } else {

            return null;
        }
    }
    public OrdersGetDto getOrderById(Long id) {
        Optional<Orders> optionalOrders = ordersRepository.findById(id);
        return optionalOrders.map(this::convertToDto).orElse(null);
    }

    public OrdersGetDto createOrder(OrdersPostDto ordersPostDto) {
        Orders orders = convertToEntity(ordersPostDto);
        Orders savedOrder = ordersRepository.save(orders);
        return convertToDto(savedOrder);
    }

    public OrdersGetDto updateOrder(Long id, OrdersPutDto ordersPutDto) {
        Optional<Orders> optionalOrders = ordersRepository.findById(id);
        if (optionalOrders.isPresent()) {
            Orders existingOrder = optionalOrders.get();
            BeanUtils.copyProperties(ordersPutDto, existingOrder);
            existingOrder.setUpdatedAt(LocalDateTime.now());
            Orders updatedOrder = ordersRepository.save(existingOrder);
            return convertToDto(updatedOrder);
        }
        return null;
    }

    public void deleteOrder(Long id) {
        ordersRepository.deleteById(id);
    }

    private OrdersGetDto convertToDto(Orders orders) {
        OrdersGetDto ordersDto = new OrdersGetDto();
        BeanUtils.copyProperties(orders, ordersDto);
        if (orders.getCreatedByUser() != null) {
            ordersDto.setUserId(orders.getCreatedByUser().getId());
        }
        if (orders.getRestaurant() != null) {
            ordersDto.setRestaurantId(orders.getRestaurant().getId());
        }
        return ordersDto;
    }

    private Orders convertToEntity(OrdersPostDto ordersPostDto) {
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersPostDto, orders);
        return orders;
    }
}
