package ru.javawebinar.topjava.to;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

public class MealTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd' 'HH:mm")
    private LocalDateTime dateTime;

    @NotBlank
    private String description;

    @NotNull
    @Range(min = 10, max = 5000)
    private int calories;

    public MealTo(){
    }

    public MealTo(Integer id, LocalDateTime dateTime, String description, int calories)
    {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public boolean isNew() {
        return id == null;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime)
    {
        this.dateTime = dateTime;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getCalories()
    {
        return calories;
    }

    public void setCalories(int calories)
    {
        this.calories = calories;
    }

    @Override
    public String toString()
    {
        return "MealTo{" + "id=" + id + ", dateTime=" + dateTime + ", description='" + description + '\'' + ", calories=" + calories + '}';
    }
}
