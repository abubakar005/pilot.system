package io.pilot.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "ted_talk")
public class TedTalk extends AbstractAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "date")
    private String date;

    @Column(name = "views")
    private Long views;

    @Column(name = "likes")
    private Long likes;

    @Column(name = "link")
    private String link;

    @Override
    public String toString() {
        return "TedTalk{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", views=" + views +
                ", likes=" + likes +
                ", link='" + link + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TedTalk)) return false;
        TedTalk tedTalk = (TedTalk) o;
        return id.equals(tedTalk.id) &&
                title.equals(tedTalk.title) &&
                author.equals(tedTalk.author) &&
                date.equals(tedTalk.date) &&
                views.equals(tedTalk.views) &&
                likes.equals(tedTalk.likes) &&
                link.equals(tedTalk.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
