package hu.palkonyves.jsfshowcase.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named
public class BananaStore {

    AtomicLong nextId = new AtomicLong(1L);
    Map<Long, Banana> bananas = new HashMap<Long, Banana>();

    @PostConstruct
    public void postConstruct() {
        synchronized (bananas) {
            createBanana(new Banana("joe", 0, 100));
            createBanana(new Banana("bobby", 10, 90));
            createBanana(new Banana("steve", 100, 0));
            createBanana(new Banana("beethoven", 20, 80));
        }
    }

    public Banana createBanana(Banana banana) {
        Banana newBanana = new Banana(banana.getName(), banana.getYellowness(),
                banana.getBrowness());
        newBanana.setId(nextId.getAndIncrement());
        bananas.put(newBanana.id, newBanana);
        return newBanana;
    }

    public List<Banana> getAllBanana() {
        return new ArrayList<Banana>(bananas.values());
    }

    public List<Banana> getBananas(int offset, int count) {
        ArrayList<Banana> allBananas = new ArrayList<Banana>(bananas.values());
        int to = offset + count > allBananas.size() ? allBananas.size()
                : offset + count;
        return allBananas.subList(offset, to);
    }

    public int countBananas() {
        return bananas.size();
    }

    public Banana changeBanana(Banana banana) {
        bananas.put(banana.getId(), banana);
        return banana;
    }
}
