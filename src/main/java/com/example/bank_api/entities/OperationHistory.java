package com.example.bank_api.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "operation_history")
@AllArgsConstructor
@NoArgsConstructor
public class OperationHistory {
    @Id
    @SequenceGenerator(name = "opHistoryIdSeq", sequenceName = "opHistories_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "opHistoryIdSeq")
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @JoinColumn(name="recipient_id", nullable = true)
    private Long recipientId;


    @Column(name = "operation_type")
    private int operationType;

    @Column(name = "summary")
    private double summary;

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    private
    LocalDateTime date;


    public OperationHistory(User userId, int operationType, double summary, LocalDateTime date){
        this.userId=userId;
        this.recipientId=null;
        //1-take, 2-put
        this.operationType=operationType;
        this.summary=summary;
        this.date=date;
    }

    public OperationHistory(User userId, Long recipientId, int operationType, double summary, LocalDateTime date){
        this.userId=userId;
        this.recipientId=recipientId;
        //1-take, 2-put
        this.operationType=operationType;
        this.summary=summary;
        this.date=date;
    }




    //Таблица должна хранить ID операции, ID пользователя, тип операции (целое число) и сумму (целое число).
    // Первичный ключ — ID операции,
    // вторичный ключ — ID пользователя.
    // Между таблицей с операциями и таблицей с балансом должна быть связь по вторичному ключу — ID пользователя.
    // Если у вас возникли сложности при выполнении этого задания, повторите модули по SQL.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
