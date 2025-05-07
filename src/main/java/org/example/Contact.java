package org.example;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Contact {
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String phoneNumber;
}

