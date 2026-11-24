package com.bush.skygate.booking;

import com.bush.skygate.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.Locale;

@Entity
@Table(name = "booking")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Booking extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "status",
            length = 25,
            nullable = false)
    private BookingStatus status = BookingStatus.CONFIRMED;

    @Column(name = "channel",
            length = 40)
    private String channel;

    @Column(name = "contact_name",
            length = 60,
            nullable = false)
    private String contactName;

    @Column(name = "contact_email",
            length = 254,
            nullable = false)
    private String email;

    @Column(name = "contact_phone",
            length = 20,
            nullable = false)
    private String contactPhone;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "language",
            length = 2,
            nullable = false)
    private BookingLanguage language = BookingLanguage.en ;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "booking_reference",
            length = 6,
            nullable = false,
            unique = true)
    private String bookingReference;

    public Booking(String channel, String contactName, String email, String contactPhone,
                   BookingLanguage language, String bookingReference){
        this.channel = channel == null ? null : channel.toLowerCase(Locale.ROOT);
        this.contactName = contactName;
        this.email = email;
        this.contactPhone = contactPhone;
        if(language != null){
            this.language = language;
        }
        this.bookingReference = bookingReference;
    }

}

