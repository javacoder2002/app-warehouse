package uz.pdp.appwarehouse.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputDto {
    private Integer id;
    private Timestamp date = Timestamp.from(Instant.now());
    private Integer warehouseId;
    private Integer supplierId;
    private String factureNumber;
}
