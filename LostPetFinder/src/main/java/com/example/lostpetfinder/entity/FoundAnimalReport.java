package com.example.lostpetfinder.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "found_animal_reports", indexes = {
        @Index(name = "idx_found_locality", columnList = "found_location"),
        @Index(name = "idx_found_species", columnList = "species")
})
public class FoundAnimalReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String species;

    @Column(length = 80)
    private String breed;

    @Column(nullable = false, length = 50)
    private String color;

    @Column(name = "found_location", nullable = false, length = 120)
    private String foundLocation;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReportStatus status = ReportStatus.ACTIVE;

    @Column(nullable = false)
    private LocalDateTime reportedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId() { return id; }
    public String getSpecies() { return species; }
    public String getBreed() { return breed; }
    public String getColor() { return color; }
    public String getFoundLocation() { return foundLocation; }
    public String getDescription() { return description; }
    public ReportStatus getStatus() { return status; }
    public LocalDateTime getReportedAt() { return reportedAt; }
    public User getUser() { return user; }
    public void setId(Long id) { this.id = id; }
    public void setSpecies(String species) { this.species = species; }
    public void setBreed(String breed) { this.breed = breed; }
    public void setColor(String color) { this.color = color; }
    public void setFoundLocation(String foundLocation) { this.foundLocation = foundLocation; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(ReportStatus status) { this.status = status; }
    public void setReportedAt(LocalDateTime reportedAt) { this.reportedAt = reportedAt; }
    public void setUser(User user) { this.user = user; }
}
