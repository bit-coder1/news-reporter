package app.reporter.dataobjects;

import java.util.List;

public class NewsFeedDO {
	private List<Articles> articles;

	private String sortBy;

	private String source;

	private String status;

	public List<Articles> getArticles() {
		return articles;
	}

	public void setArticles(List<Articles> articles) {
		this.articles = articles;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ClassPojo [articles = " + articles + ", sortBy = " + sortBy + ", source = " + source + ", status = "
				+ status + "]";
	}
}