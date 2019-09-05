#!/usr/bin/gawk -f
BEGIN {
	print "<html>";
	print "";
	print "<head>";
	print "<title>Building Permits</title>";
	print "</head>";
	print "";
	print "<body>";
	print "<h1>Building Permits</h1>";
	print "";
	print "<table style=\"width: 100%; border: 1px solid black; padding: 15px\">"
	print "<tr>";
	print "<th>Date</th>";
	print "<th>Subdivision Name</th>";
	print "<th>Lot</th>";
	print "<th>Block</th>";
	print "<th>Value</th>";
	print "</tr>";
	
}
{
	FPAT = "([^,]*)|(\"[^\"]+\")";
	IGNORECASE = 1;
	if ($3 ~ /single family/) {
		print "<tr>";
		print "<td>", $1, "</td>";
		print "<td>", $5, "</td>";
		print "<td>", $6, "</td>";
		print "<td>", $7, "</td>";
		print "<td>", substr($8, 2, length($8) - 2), "</td>";
		print "</tr>";
	}

}
END {
	print "</table>";
	print "</body>";
	print "</html>";
}
