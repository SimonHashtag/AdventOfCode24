using DelimitedFiles
using DataFrames

# Load data
file = open("./data/day2.txt", "r")
lines = readlines(file)
reports = [parse.(Int64, element) for element in split.(lines, " ")]

is_safe = function(report_diff::Vector{Int64})
    if (all(report_diff.>0) || all(report_diff.<0)) & all(abs.(report_diff).<4)
        return true
    else
        return false
    end
end

problem_dampener = function(report::Vector{Int64})
    for i in 1:length(report)
        if is_safe(diff(report[Not(i)]))
            return true
        end
    end
    return false
end

test_reports = [7 6 4 2 1; 1 2 7 8 9; 9 7 6 2 1; 1 3 2 4 5; 8 6 4 4 1; 1 3 6 7 9]
test_reports = Vector{eltype(test_reports)}[eachrow(test_reports)...]
test_diffs = diff.(test_reports)
is_safe.(test_diffs)
problem_dampener.(test_reports)

report_diffs = diff.(reports)
sum(is_safe.(report_diffs))
sum(problem_dampener.(reports))