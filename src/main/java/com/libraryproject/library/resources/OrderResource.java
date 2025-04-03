package com.libraryproject.library.resources;

import com.libraryproject.library.entities.dto.OrderDTO;
import com.libraryproject.library.entities.dto.OrderMinDTO;
import com.libraryproject.library.entities.projections.OrderProjection;
import com.libraryproject.library.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    @Autowired
    private OrderService service;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Page<OrderMinDTO>> findAll(Pageable pageable) {
        Page<OrderMinDTO> page = service.findAll(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/between")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Page<OrderMinDTO>> findBetween(Pageable pageable, @RequestParam(name = "minDate", defaultValue = "") String minDate,
                                                             @RequestParam(name = "maxDate", defaultValue = "")String maxDate){
        Page<OrderMinDTO> page = service.findBetween(pageable, minDate, maxDate);
        return ResponseEntity.ok().body(page);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_CLIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id){
        OrderDTO order = service.findById(id);
        return ResponseEntity.ok().body(order);
    }

}
