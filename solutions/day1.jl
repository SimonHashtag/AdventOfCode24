# Utility functions
using DelimitedFiles
using DataFrames

# Load data
file = open("./data/day1.txt", "r")
lines = readlines(file)
df = DataFrame([(a=parse(Float32, a), b=parse(Float32, b)) for (a ,b) in split.(lines, "   ")])

test_df = DataFrame((a=[3,4,2,1,3,3], b=[4,3,5,3,9,3]))

get_sorted_df = function(df)
    sorted_df = DataFrame((a=sort(df[:,1]), b=sort(df[:,2])))
    return sorted_df
end

get_similarity_score = function(df)
    similarity_score = 0
    for line in df[:,1]
        similarity_score += line * count(isequal(line), df[:,2])
    end
    return similarity_score
end

sorted_df = get_sorted_df(df)
total_distance = sum(abs.(sorted_df[:,1] .- sorted_df[:,2]))

result = get_similarity_score(df)
