package com.webwizards.screenseekers.utils;
/**
 * Java Class: Recommender
 * 
 * Author: Luis Miguel Miranda
 * 
 * Date: 24/01/2023
 * 
 * Description: This class consists on a list of methods that will calculate the prediction of a movie rating
 * by using the User-based Collaborative Filtering Algorithm
 * 
 * Version: 1.0
 **/



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.model.Rating;
import com.webwizards.screenseekers.model.User;


public class Recommender {

	private static final int NUM_NEIGHBORS = 10;
	private static final double MIN_ACCEPTABLE_RATING = 3.5;
	private HashMap<Long, HashMap<Long,Double>> ratings;
	
	
	public Recommender() {
		
	}
	
	public Recommender(HashMap<Long, HashMap<Long,Double>> ratings) {
		this.ratings = ratings;
	}
	
	/*
	 * method: getCommonRatedMoviesList
	 * params:
	 * {int a} -> userId
	 * {int b} -> userId
	 * return:
	 * {List<Long>} -> list of MovieIds that both users rated.
	 */
	public List<Long> getCommonRatedMoviesList (long a, long b) {
		
		List<Long> commonRatedMovies = new ArrayList<>();
		
		HashMap<Long,Double> moviesRatedA = ratings.get(a);
		HashMap<Long,Double> moviesRatedB = ratings.get(b);
		
		for(Map.Entry<Long, Double> entryA: moviesRatedA.entrySet()) {
			if (moviesRatedB.containsKey(entryA.getKey())) {
				commonRatedMovies.add(entryA.getKey());
			}
		}
		
		return commonRatedMovies;
		
	}
	
	/*
	 * method: calculateAverageRating
	 * params:
	 * {int a} -> userId
	 * return:
	 * {double} -> the average rating for user with userId 'a'
	 */
	public double calculateAverageRating(long a) {
		
		double average = 0.0;
		
		HashMap<Long,Double> moviesRatedA = ratings.get(a);
		
		for(Map.Entry<Long,Double> entry : moviesRatedA.entrySet()) {
			
			average += entry.getValue();
			
		}
		
		average = average / (double)moviesRatedA.size();
		
		return average;
		
	}
	
	/*
	 * method: calculateSimilarity
	 * params:
	 * {int a} -> userId
	 * {int b} -> userId
	 * {List<Long> crm} -> crm = common rated movies
	 * return:
	 * {double} -> the similarity between users 'a' and 'b' with a list of common rated movies
	 */
	public double calculateSimilarity(long a, long b, List<Long> crm) {
		
		double numerator = 0.0;
		double denom1 = 0.0;
		double denom2 = 0.0;
		
		HashMap<Long,Double> moviesRatedA = ratings.get(a);
		HashMap<Long,Double> moviesRatedB = ratings.get(b);
		
		for(int i=0; i< crm.size(); i++) {
			
			numerator += (moviesRatedA.get(crm.get(i)))*(moviesRatedB.get(crm.get(i)));
			
			denom1	  += Math.pow(moviesRatedA.get(crm.get(i)), 2);
			denom2	  += Math.pow(moviesRatedB.get(crm.get(i)), 2);
			
		}
		
		denom1 = Math.sqrt(denom1);
		denom2 = Math.sqrt(denom2);
		
		if (crm.size()> 1 && numerator !=0 && denom1 != 0 && denom2 != 0) {
			return numerator / (denom1 * denom2);
		}
		return -100;
		
	}
	
	/*
	 * method: sortingHashMap
	 * params:
	 * {HashMap<Integer,Double> unsortedHashMap} -> unsorted HashMap
	 * return:
	 * {HashMap<Integer,Double>} -> sorted HashMap by Value in descending order
	 */
	public HashMap<Long, Double> sortingHashMap(HashMap<Long,Double> unsortedHashMap){
		
		List<Map.Entry<Long, Double>> list = new LinkedList<>(unsortedHashMap.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<Long,Double>>(){
			
			@Override
			public int compare(Map.Entry<Long, Double> o1,
							   Map.Entry<Long, Double> o2) {
				
				return (o2.getValue()).compareTo(o1.getValue());
			}
			
		});
		
		HashMap<Long,Double> sortedHashMap = new LinkedHashMap<>();
		for (Map.Entry<Long, Double> entry: list) {
			
			sortedHashMap.put(entry.getKey(), entry.getValue());
			
		}
		
		
		return sortedHashMap;
		
	}
	
	/*
	 * method: getUserSimilarities
	 * params:
	 * {int a} -> userId
	 * return:
	 * {HashMap<Integer,Double>} -> HashMap which Key is the other userId we are comparing with and the Value is the similarity value
	 */
	public HashMap<Long, Double > getUsersSimilarities(long a){
		
		HashMap<Long,Double> userSimilarities = new HashMap<>();
		
		for(Map.Entry<Long, HashMap<Long,Double>> entry: ratings.entrySet()) {
			
			if (a != entry.getKey()) {
			
				List<Long> crm = getCommonRatedMoviesList(a,entry.getKey());
				
				double similarity = calculateSimilarity(a,entry.getKey(),crm);
				
				//don't include users with similarity score -100.0
				if (similarity != -100.0) {
					
					userSimilarities.put(entry.getKey(), similarity);
					
				}

				
			}
			
		}
		
		userSimilarities = sortingHashMap(userSimilarities);
		
		return userSimilarities;
		
	}
	
	/*
	 * method: calculatePrediction
	 * params:
	 * {int a} -> userId
	 * {int movieIdx} -> movie we want to predict its rating.
	 * return:
	 * {double} -> predicted rating
	 */
	public double calculatePrediction(long a, long movieIdx) {
		
		//First we are going to calculate the similarities and let's set a limit of Neighbors
		HashMap<Long, Double> similarUsers = getUsersSimilarities(a);
		
		int numUsers = Math.min(similarUsers.size(), NUM_NEIGHBORS);
		
		if (numUsers == 0) {
			return 0;
		}
		
		double rating; 
		
		double numerator = 0.0;
		double denom = 0.0;
		
		int j=0;
		
		for(Map.Entry<Long, Double> user: similarUsers.entrySet()) {
			
			if (j<numUsers) {
				
				double avgRj = calculateAverageRating(user.getKey());
				
				double similarityWithJ = user.getValue();
				
				double ratingFromUser = ratings.get(user.getKey()).get(movieIdx) == null ? 0 : ratings.get(user.getKey()).get(movieIdx) ;
				
				if (ratingFromUser > 0) {
				
					numerator += similarityWithJ * (ratingFromUser  - avgRj);
					
					denom += Math.abs(similarityWithJ);
					
					j++;
					
				}
				
			}else {
				
				break;
				
			}			
		
		}
		
		double avgRa = calculateAverageRating(a);
		
		
		rating = avgRa + (numerator / denom);
		
		return rating;
		
	}
	
	/*
	 * method: getRecommendedMovieList
	 * params:
	 * {List<Movie>} -> list of all movies available
	 * {int a} -> userId
	 * return:
	 * {List<Movie>} -> recommended movies
	 */
	public List<Movie> getRecommendedMovieList(List<Movie> allMovies ,long a) {
		
		//Before making the predictions, first we need to get the users with the highest similarities
		
		List<Movie> recommendedMovieList = new ArrayList<>();
		
		for (int i = 0; i < allMovies.size() ; i++) {
			
			Movie movie = allMovies.get(i);
			
			if (ratings.get(a).get(movie.getId()) == null) {
				
				double predictedRating = calculatePrediction(a,movie.getId());
				
				if (predictedRating > MIN_ACCEPTABLE_RATING) {
					
					recommendedMovieList.add(movie);
					
				}

			}
			
		}
		
		return recommendedMovieList;
		
	}
	
	/*
	 * method: setRatings
	 * return:
	 * {HashMap<Long, HashMap<Long, Double>>} -> rating list
	 */
	public HashMap<Long, HashMap<Long, Double>> getRatings() {
		return ratings;
	}
	
	/*
	 * method: setRatings
	 * params:
	 * {List<User>} -> total of users
	 * return:
	 * {void}
	 */
	public void setRatings(List<User> users) {
		HashMap<Long,HashMap<Long,Double>> ratingList = new HashMap<>();
		
		for(User user : users) {
			
			Set<Rating> ratings = user.getRatings();
			
			if (ratings != null) {
				
				HashMap<Long,Double> ratingMovie = new HashMap<>();
				
				for (Rating rating: user.getRatings()) {
					
					ratingMovie.put(rating.getMovie().getId(),rating.getUserRating()*1.0);
					
				}
				
				ratingList.put(user.getId(), ratingMovie);
				
			}
			
			
		}
		
		
		this.ratings = ratingList;
	}
	
}
