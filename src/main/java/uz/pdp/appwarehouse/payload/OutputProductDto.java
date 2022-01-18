package uz.pdp.appwarehouse.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputProductDto {
    private Integer id;
    private Integer productId;
    private Double amount;
    private Double price;
    private Integer currencyId;
    private Integer outputId;
}
