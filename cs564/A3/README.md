# A3 - D3 programming assignment
Tyler Bevan

## Files
 - index.html - Launch page, mostly for my convienience.
 - helloworld.html - Contains the histogram graph.
 - barchart.html - Contains the simple bar chart, uses random numbers as data.
 - graph.html - Scatter plot of stock values.
 - tree.html - Simple tree generated from embedded test data.
 - treemap.html - Treemap of the directory data.
 - radialtree.html - Radial tree of the directory data.
 - stream.html - Stream graph of the word data.
 - spline.html - Simple spline defined by 5 custom points.
 - map.html - Simple us map with the states colored by population.
 - csvweb.html - Line graph generated from a remote csv location.
 - resizemap.html - US map with states scaled down based on population.

## Notes
With the exception of the simple tree, all the data used by my visualizations is either generated on the fly or is read from data files. For the purposes of readability, I only used the directories for the treemap and radial tree. There are so many files that they aren't visible when rendered. I am however using the file count of the folders as the folder "size" when creating the treemap. In order to work with the folder data more easily, I quickly converted the data to YAML format (mainly because I'm more familiar with it than with JSON). I imported a yaml.js library to import the file into the equivilent javascript object.

The streamgraph needed to have multiple X values, so I took the top ten words from the words file and put them in a csv file. I added rows of zeros before and after the data row such that the graph wouldn't just be flat. The data is in data/words.csv.

In order to render the maps, I found a json file with the state shapes. It is included in data/us-states.json. Population data was pulled from census data for 2017.
