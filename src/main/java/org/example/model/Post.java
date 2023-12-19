package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @Column("post_id")
    Integer postId;
    String title;

    public Post(String title) {
        this.title = title;
    }
}
