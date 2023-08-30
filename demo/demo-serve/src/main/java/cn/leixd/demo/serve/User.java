package cn.leixd.demo.serve;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private Long userId;
    private String userName;
}
