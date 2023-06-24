package com.driver.Repository;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.*;

public class HotelRepo {

    Map<String, Hotel> hotelMap = new HashMap<>();
    Map<String, User> userMap = new HashMap<>();
    Map<String, Booking> bookingMap = new HashMap<>();
    Map<Integer, List<Booking>> userBookingsMap = new HashMap<>();

    public String addHotel(Hotel hotel) {
        if(hotel == null || hotel.getHotelName() == null || hotelMap.containsKey(hotel.getHotelName()))
            return "FAILURE";

        hotelMap.put(hotel.getHotelName(),hotel);
        return "SUCCESS";
    }
    public Integer addUser(User user) {

        userMap.put(user.getName(),user);
        return user.getaadharCardNo();
    }
    public String getHotelWithMostFacilities() {
        String ans = "";
        int max = 0;

        for(Hotel hotel : hotelMap.values()){
            if(hotel.getFacilities().size() > max ||
                    (hotel.getFacilities().size() == max && ans != null && ans.compareTo(hotel.getHotelName()) > 0)
            )
            {
                max = hotel.getFacilities().size();
                ans = hotel.getHotelName();
            }

        }

        return ans;
    }

    public int bookARoom(Booking booking) {
        UUID uuid = UUID.randomUUID();
        booking.setBookingId(uuid.toString());

        if(booking.getNoOfRooms() > hotelMap.get(booking.getHotelName()).getAvailableRooms() )
            return - 1;

        bookingMap.put(uuid.toString(),booking);


        List<Booking> userBookings = userBookingsMap.get(booking.getBookingAadharCard());

        if(userBookings == null)
            userBookings = new ArrayList<>();

        userBookings.add(booking);

        userBookingsMap.put(booking.getBookingAadharCard(),userBookings);


        return booking.getNoOfRooms() * hotelMap.get(booking.getHotelName()).getPricePerNight();
    }

    public int getBookings(Integer aadharCard) {

        return userBookingsMap.get(aadharCard).size();

    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {

        Hotel hotel= hotelMap.get(hotelName);
        List<Facility> facilities = hotel.getFacilities();

        for(Facility facility1 :newFacilities){
            if(!facilities.contains(facility1))
                facilities.add(facility1);
        }

        hotel.setFacilities(facilities);

        return hotel;
    }

}
