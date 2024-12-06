read_string_data = function(file_name::String)
    path = string("./data/", file_name, ".txt")
    input = open(path) do file
        read(file, String)
    end
    return(input)
end