import java.text.DecimalFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Solution {
    public Solution() {
        super();
    }

    public static void main(String[] args) {
        String S =
            "photo.jpg Warsaw, 2013-09-05 14:08:15\n" + "john.png London, 2015-06-20 15:13:22\n" + "myFriends.png Warsaw, 2013-09-05 14:07:13\n" +
            "eiffel.jpg Paris, 2015-07-23 08:03:02\n" + "pisatower.jpg Paris, 2015-07-22 23:59:59\n" + "BOB.jpg London, 2015-08-05 00:02:03\n" +
            "notradame.png Paris, 2015-09-01 12:00:00\n" + "me.jpg Warsaw, 2013-09-06 15:40:22\n" + "a.png Warsaw, 2016-02-13 13:33:50\n" +
            "b.jpg Warsaw, 2016-01-02 15:12:22\n" + "c.jpg Warsaw, 2016-01-02 14:34:30\n" + "d.jpg Warsaw, 2016-01-02 15:15:01\n" +
            "e.png Warsaw, 2016-01-02 09:49:09\n" + "f.png Warsaw, 2016-01-02 10:55:32\n" + "g.jpg Warsaw, 2016-01-02 22:13:11\n" +
            "h.jpg Warsaw, 2016-01-02 22:12:11\n" + "i.jpg Warsaw, 2016-01-02 22:59:59";

        System.out.println(solution(S));
    }

    public static String solution(String S) {
        String[] photos = S.split("\n");
        //System.out.println(Arrays.toString(photos));
        int photoNumber = 1;
        List<Photo> photoList = new ArrayList<>();
        for (String s : photos) {
            String[] tokens = s.split("\\s");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(tokens[2] + " " + tokens[3], formatter);

            photoList.add(new Photo(photoNumber, tokens[0], tokens[1].substring(0, tokens[1].lastIndexOf(',')), dateTime));
            photoNumber++;
        }

        // Collections.sort(photoList,new PhotoComparator());

        Map<String, List<Photo>> map = new HashMap<String, List<Photo>>();
        for (Photo photo : photoList) {
            String city = photo.city;
            if (map.containsKey(city)) {
                List<Photo> list = map.get(city);
                list.add(photo);

            } else {
                List<Photo> list = new ArrayList<>();
                list.add(photo);
                map.put(city, list);
            }
        }
        photoList.clear();

        map.forEach((k, v) -> Collections.sort(v, new PhotoComparator()));
        for (String city : map.keySet()) {
            List<Photo> sortedPhoto = map.get(city);
            int digits = getSize(sortedPhoto.size());
            int number = 1;
            for (Photo p : sortedPhoto) {
                p.setName(p.getCity() + leadZeros(number, digits) + p.getName().substring(p.getName().lastIndexOf(".")));
                number++;
                photoList.add(p);
            }
        }

        Collections.sort(photoList, new Comparator<Photo>() {
            @Override
            public int compare(final Photo mcc1, final Photo mcc2) {
                // TODO Implement this method
                return Integer.valueOf(mcc1.getId()).compareTo(mcc2.getId());
            }
        });
        return photoList.stream()
                        .map(c -> c.getName())
                        .collect(Collectors.joining("\n"))
                        .toString();
    }

    public static String leadZeros(int number, int size) {
        char[] zeros = new char[size];
        Arrays.fill(zeros, '0');
        DecimalFormat df = new DecimalFormat(String.valueOf(zeros));
        return df.format(number);
    }

    public static int getSize(long number) {
        return (int) (Math.floor(Math.log10(number))) + 1;
    }

    public static class Photo {
        int id;
        String name;
        String city;
        LocalDateTime photoDate;

        public Photo(int id, String photoName, String cityName, LocalDateTime photoTaken) {
            this.id = id;
            this.name = photoName;
            this.city = cityName;
            this.photoDate = photoTaken;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        void setName(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        void setCity(String city) {
            this.city = city;
        }

        String getCity() {
            return city;
        }

        void setPhotoDate(LocalDateTime photoDate) {
            this.photoDate = photoDate;
        }

        LocalDateTime getPhotoDate() {
            return photoDate;
        }

        public String toString() {
            return this.getName() + "-" + this.getId();
        }
    }

    public static class PhotoComparator implements Comparator<Photo> {
        @Override
        public int compare(final Photo mcc1, final Photo mcc2) {
            return mcc1.getPhotoDate().compareTo(mcc2.getPhotoDate());
        }
    }
}
