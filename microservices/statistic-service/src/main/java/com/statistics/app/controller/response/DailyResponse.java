package com.statistics.app.controller.response;


import com.statistics.app.controller.response.base.BaseApiResponse;
import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DailyResponse extends BaseApiResponse {
    private BigDecimal totalAmountInDay;
    private Integer totalItemsCountDay;
}
