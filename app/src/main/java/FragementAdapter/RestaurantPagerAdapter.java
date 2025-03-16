package FragementAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import data.Restaurant;

public class RestaurantPagerAdapter extends FragmentStateAdapter {

    private final String[] tabTitles = new String[]{"Infos", "Réserver", "Avis"};
    private final Restaurant restaurant;
    public RestaurantPagerAdapter(@NonNull FragmentActivity fragmentActivity, Restaurant restaurant) {
        super(fragmentActivity);
        this.restaurant = restaurant;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return  ReservationFragment.newInstance(restaurant);
            case 2:
                return  ReviewsFragment.newInstance(restaurant);
            default:
                return  InfoFragment.newInstance(restaurant);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public String getPageTitle(int position) {
        return tabTitles[position];
    }
}