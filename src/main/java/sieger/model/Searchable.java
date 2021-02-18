package sieger.model;
/**
 * Interface of tournament for searching.
 * 
 * @author Chen Zhang
 *
 */
public interface Searchable {
	/**
	 * Get the title of tournament.
	 * 
	 * @return Return the title in String.
	 */
	abstract public String Title();
	/**
	 * Get the information of tournament.
	 * 
	 * @return Return the information in String.
	 */
	abstract public String Information();
}
