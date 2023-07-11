package prasanna.application.openingapptask.model

data class LinksData(
    val overall_url_chart: OverallUrlChart,
    val recent_links: List<RecentLink>,
    val top_links: List<TopLink>
)