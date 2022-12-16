package movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import input.MovieInput;

import java.io.IOException;
import java.util.ArrayList;

public class Movie {
    public static final int                MAX_RATING = 5;
    private             String             name;
    private             int                year;
    private             int                duration;
    private             ArrayList<String>  genres;
    private             ArrayList<String>  actors;
    private             ArrayList<String>  countriesBanned;
    private             int                numLikes;
    private             int                numRatings;
    @JsonSerialize(using = RatingSerializer.class)
    private             double             rating;
    @JsonIgnore
    private             ArrayList<Integer> ratings;

    public Movie(final MovieInput source) {
        this.name = source.getName();
        this.year = source.getYear();
        this.duration = source.getDuration();
        this.genres = source.getGenres();
        this.actors = source.getActors();
        this.countriesBanned = source.getCountriesBanned();
        this.rating = 0.00f;
        this.numLikes = 0;
        this.numRatings = 0;
        this.ratings = new ArrayList<>();
    }

    public Movie(final Movie source) {
        this.name = source.getName();
        this.year = source.getYear();
        this.duration = source.getDuration();
        this.genres = new ArrayList<>(source.getGenres());
        this.actors = new ArrayList<>(source.getActors());
        this.countriesBanned = new ArrayList<>(source.getCountriesBanned());
        this.rating = source.getRating();
        this.numLikes = source.getNumLikes();
        this.numRatings = source.getNumRatings();
        this.ratings = new ArrayList<>(source.getRatings());
    }

    public ArrayList<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Integer> ratings) {
        this.ratings = ratings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public boolean isBanned(String country) {
        return countriesBanned.contains(country);
    }

    public void addLike() {
        numLikes++;
    }

    public void addRating(final int rating) {
        this.ratings.add(rating);
        numRatings++;

        double sum = 0;
        for (int i = 0; i < numRatings; i++) {
            sum += ratings.get(i);
        }

        this.rating = sum / numRatings;
    }

    private static class RatingSerializer extends JsonSerializer<Double> {
        @Override
        public void serialize(
                Double value, JsonGenerator gen, SerializerProvider serializers
                             ) throws IOException {
            gen.writeNumber(String.format("%.2f", value));
        }
    }
}
