package net.yixin.witty_vet.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private String stars;
    private String feedback;
    private Long veterinarianId;
    private String veterinarianName;
    private String veterinarianSpecialization;
    private Long reviewerId;
    private String reviewerName;
    private byte[] veterinarianImage;
    private byte[] reviewerImage;
}
