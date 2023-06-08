package com.example.userservice.projection;

import com.example.commonservice.model.CardDetail;
import com.example.commonservice.model.User;
import com.example.commonservice.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    @QueryHandler
    public User getUserPaymentDetail(GetUserPaymentDetailsQuery query){

        // Ideally Get the details from the DB
        CardDetail cardDetail
                = CardDetail.builder()
                .name("nolja ya")
                .validUntilYear(2022)
                .validUntilMonth(1)
                .cardNumber("123456789")
                .cvv(111)
                .build();

        return User.builder()
                .userId(query.getUserId())
                .firstName("nolja")
                .lastName("ya")
                .cardDetail(cardDetail)
                .build();
    }
}
