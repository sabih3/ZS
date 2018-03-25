package netaq.com.zayedsons.network.model.responses;

import java.util.List;

/**
 * Created by sabih on 24-Mar-18.
 */

public class ResponseEventGallery extends BaseResponse {

    private List<Albums> Albums;

    public List<Albums> getAlbums() {
        return this.Albums;
    }

    public class Albums {

        public String ID;

        public String Lang;

        public String ImageURL;

        public String Title;

        public String Details;

        public int TotalItems;

        public List<Gallery> Gallery;

        public String getID() {
            return ID;
        }

        public String getLang() {
            return Lang;
        }

        public String getImageURL() {
            return ImageURL;
        }

        public String getTitle() {
            return Title;
        }

        public String getDetails() {
            return Details;
        }

        public int getTotalItems() {
            return TotalItems;
        }

        public List<Gallery> getGallery() {
            return Gallery;
        }

        public class Gallery {

            public String ID;

            public String Lang;

            public String Title;

            public String Details;

            public String FileURL;

            public String getID() {
                return ID;
            }

            public String getLang() {
                return Lang;
            }

            public String getTitle() {
                return Title;
            }

            public String getDetails() {
                return Details;
            }

            public String getFileURL() {
                return FileURL;
            }
        }
    }
}
