package lab13.iss1.Domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private UUID id;
    private String email;
    private UUID gameID;
    private OrderStatus status;
    private LocalDateTime purchaseDate;

    public Order(String email, UUID gameID, OrderStatus status, LocalDateTime purchaseDate) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.gameID = gameID;
        this.status = status;
        this.purchaseDate = purchaseDate;
    }

    public Order(String email, UUID gameID, OrderStatus status) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.gameID = gameID;
        this.status = status;
        this.purchaseDate = LocalDateTime.now();
    }

    public Order(String email, UUID gameID) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.gameID = gameID;
        this.status = OrderStatus.PENDING;
        this.purchaseDate = LocalDateTime.now();
    }

    public Order(UUID id, String email, UUID gameID, OrderStatus status, LocalDateTime purchaseDate) {
        this.id = id;
        this.email = email;
        this.gameID = gameID;
        this.status = status;
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", gameID=" + gameID +
                ", status=" + status +
                ", purchaseDate=" + purchaseDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(email, order.email) && Objects.equals(gameID, order.gameID) && status == order.status && Objects.equals(purchaseDate, order.purchaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, gameID, status, purchaseDate);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getGameID() {
        return gameID;
    }

    public void setGameID(UUID gameID) {
        this.gameID = gameID;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
