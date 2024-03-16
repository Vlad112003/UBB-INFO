package ro.ubbcluj.cs.map.lab82nd.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class Message extends Entity<UUID>{

    private UUID from;
    private UUID to;
    private String message;
    private LocalDateTime date;

    private UUID replyTo;

    public Message(UUID from, UUID to, String message){
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = LocalDateTime.now();
        this.setId(UUID.randomUUID());

    }

    public Message(UUID from, UUID to, String message, UUID replyTo){
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = LocalDateTime.now();
        this.replyTo = replyTo;
        this.setId(UUID.randomUUID());

    }



    public Message(UUID from, UUID to, String message, LocalDateTime date){
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
        this.setId(UUID.randomUUID());
    }

    public Message(UUID from, UUID to, String message, LocalDateTime date, UUID replyTo){
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
        this.replyTo = replyTo;
        this.setId(UUID.randomUUID());
    }

    public UUID getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(UUID replyTo) {
        this.replyTo = replyTo;
    }





    public UUID getId(){
        return super.getId();
    }

    public void setId(UUID id){
        super.setId(id);
    }
    public UUID getFrom() {
        return from;
    }

    public void setFrom(UUID from) {
        this.from = from;
    }

    public UUID getTo() {
        return to;
    }

    public void setTo(UUID to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", replyTo=" + replyTo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Message message1 = (Message) o;
        return Objects.equals(from, message1.from) && Objects.equals(to, message1.to) && Objects.equals(message, message1.message) && Objects.equals(date, message1.date) && Objects.equals(replyTo, message1.replyTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), from, to, message, date, replyTo);
    }
}
