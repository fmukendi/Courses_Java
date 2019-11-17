package com.mukeapps.microservices.currencyexchangeservice.repository;


import com.mukeapps.microservices.currencyexchangeservice.model.ExchangeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {

   ExchangeValue findByFromAndTo(String from, String to); // JPA is capable to implement this method on his own !!!
}
