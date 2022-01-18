package uz.pdp.appwarehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appwarehouse.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Measurement - o'lchov <br>
 * Mahsulotning o'lchov birligi <br>
 * masalan: kg, metr, litr, dona ...<br>
 * id - 1, 2, ...<br>
 * name - kg, litr, ...<br>
 * active - true yoki false : shunday o'lchov birligi bor bo'lishini istasak true.
 * */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Measurement extends AbsEntity {

}

