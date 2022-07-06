package com.statistics.app.domain.base;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Random;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Where(
        clause = "deleted = 0"
)
public abstract class StatisticBaseDomain {

    public static final String SOFT_DELETE_CLAUSE = "deleted = 0";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @CreationTimestamp
    private LocalDateTime createTime;

    @NotNull
    private Long createdBy = 0L;

    @UpdateTimestamp
    private LocalDateTime lastUpdateTime;

    private Long lastUpdatedBy;

    @Column(
            name = "client_ip"
    )
    @NotNull
    private  String clientIP = generateIpAddress();

    @NotNull
    private Boolean deleted = false;

    @Column(
            name = "deletion_token"
    )
    @NotNull
    private Long deletionToken = 0L;

    @NotNull
    private String correlationId = "0";

    public String generateIpAddress(){
        Random r = new Random();
        return r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatisticBaseDomain that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
