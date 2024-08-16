package org.example.administrationservice.models.mail;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MailDTO {
    private List<String> mailTo = new ArrayList<>();
    private List<String> mailCc = new ArrayList<>();
    private String subject;
    private String content;
}
