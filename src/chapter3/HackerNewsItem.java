package chapter3;

public class HackerNewsItem {
	private String title;

	private String url ;
	private String author;
	private int score;
	private int position ;
	private int id ;
	
	public HackerNewsItem(String title, String url, String author, int score, int position, int id) {
		super();
		this.title = title;
		this.url = url;
		this.author = author;
		this.score = score;
		this.position = position;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}
