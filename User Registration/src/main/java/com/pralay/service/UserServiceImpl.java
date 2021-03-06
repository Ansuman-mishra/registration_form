package com.pralay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pralay.entity.Contact;
import com.pralay.repository.ContactRepository;
import com.pralay.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CountryRepository countryRepo;
	
	@Autowired
	private StateRepository stateRepo;

	@Autowired
	private CityRepository cityRepo;
	
	@Override
	public Map<Integer, String> findCountries(){
		List<Country> countriesList = countryRepo.findAll();
		Map<Integer, String> countries = new HashMap<>();
		countries.forEach(country ->{
			countries.put(country.getCountryId(), country.getCountryName());
		});
		return countries;
	}
	

	@Override
	public Map<Integer, String> findStates(Integer countryId){
		Map<Integer, String> states = new HashMap<>();
		List<State> statesList = stateRepo.findBycountryId(countryId);
		statesList.forEach(state ->{states.put(state.getStateId(), country.getStateName());});
		return states;
	}
	
	@Override
	public Map<Integer, String> findCities(Integer stateId){
		Map<Integer, String> cities = new HashMap<>();
		List<City> citiesList = cityRepo.findByStateId(stateId);
		statesList.forEach(city ->{cities.put(cities.getStateId(), city.getCityName());});
		return cities;
	}
	
	@Override
	public boolean isEmailUnique(String emailId){
		User userDetails = userRepo.findByEmailId(emailId);
		return userDetails.getUserId()== null;
	}
	
	
	@Override
	public boolean saveUser(User user) {
		user.setPassword(passwordGenerator());
		user.setAccountStatus("LOCKED");
		User userObj = userRepo.save(user);
		return userObj.getUserId()!= null;
	}
	
	private String passwordGenerator() {
		char[] password = new char[5];
		String alphaNumeric = "ABCDEFGHabcdefgh0123456789";
		Random randomPwd = new Random();
		for(int i=0; i<5; i++) {
			password[i] =alphaNumeric.charAt(randomPwd.nextInt(alphaNumeric.length()));
		}
		System.out.println(password.toString());
		return password.toString();
	}
	
	@Override
	public String loginCheck(String email, String pwd) {
		User userDetails = userRepo.findByEmailIdAndPassword(emailId, pwd);
		if(userDetails != null) {
			if(userDetails.getAccountStatus().equals("LOCKED")) {
				return "ACCOUNT_LOCKED"
			}else {
				return "LOGIN_SUCCESS";
			}
		}
		return "Invalid Credentials";
	}
	@Override
	public boolean isTempPwdValid(String email, String tempPwd) {
		User userDetails= userRepo.findByEmailIdAndPassword(email,tempPwd);
		return userDetails.getUserId()!=null;
	}
	
	@Override
	public boolean unlockAccount(String email, String newPwd) {
		User userDetails = userRepo.findByEmailId(email);
		if(userDetails !=null) {
			return userDetails.getPassword();
		}
		return null;
	}	
	
}


	






























