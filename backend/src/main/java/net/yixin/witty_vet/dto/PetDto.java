package net.yixin.witty_vet.dto;

import lombok.Data;

@Data
public class PetDto {
    private Long id;
    private String name;
    private int age;
    private String type;
    private String color;
    private String breed;
}
