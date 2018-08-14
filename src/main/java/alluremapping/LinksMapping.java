package alluremapping;

import allureDTO.LinksDTO;
import alluremodel.Links;

public class LinksMapping {
    public static LinksDTO fromJson(Links links) {
        return new LinksDTO(
                links.getName(),
                links.getUrl(),
                links.getType());
    }
}
