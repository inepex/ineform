package com.inepex.example.ContactManager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.inepex.example.ContactManager.shared.MeetingType;
import com.inepex.ineForm.annotations.Kvo_SearchParam;

@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Kvo_SearchParam
    private Long id;

    @Kvo_SearchParam
    @Column(nullable = false)
    private Long meetingTimestamp;

    @Kvo_SearchParam
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Kvo_SearchParam
    @ManyToOne
    @JoinColumn(nullable = false)
    private Company company;

    @Kvo_SearchParam
    @ManyToOne
    @JoinColumn(nullable = false)
    private Contact contact;

    @Kvo_SearchParam
    @Column(nullable = false)
    private MeetingType meetingType;

    @Lob
    private String description;

    public Meeting() {}

    public Meeting(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMeetingTimestamp(Long meetingTimestamp) {
        this.meetingTimestamp = meetingTimestamp;
    }

    public Long getMeetingTimestamp() {
        return meetingTimestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MeetingType getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(MeetingType meetingType) {
        this.meetingType = meetingType;
    }
}
