include("./functions.jl")
using DataFrames

test_data = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
test_data2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
data = read_string_data("day3")


wrap_data_with_dos = function(data)
    return "do()"*data*"don't()"
end

filter_illegal_muls = function(data)
    wrapped_data = wrap_data_with_dos(data)
    filtered_memory = collect(eachmatch(r"(?<=do\(\))((.|\n)*?)(?=don't\(\))", wrapped_data))
    legal_data = join(getfield.(filtered_memory, :match))
    return legal_data
end

clean_memory = function(data)
    filtered_memory = collect(eachmatch(r"(?<=mul\()\d+,\d+(?=\))", data))
    cleaned_memory = DataFrame([(a=parse.(Int64, a), b=parse.(Int64, b)) for (a, b) in split.(getfield.(filtered_memory, :match), ",")])
    return cleaned_memory
end

calculate_multiplications = function(mul_df::DataFrame)
    return sum(mul_df[:,1] .* mul_df[:,2])
end

clean_df = clean_memory(data)
multiplication = calculate_multiplications(clean_df)

legal_data = filter_illegal_muls(data)
clean_df = clean_memory(legal_data)
multiplication = calculate_multiplications(clean_df)