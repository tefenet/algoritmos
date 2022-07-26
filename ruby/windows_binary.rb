#!/usr/bin/env ruby
# frozen_string_literal:false

def self.summa(number_of_windows)
  temp_results = [0, 0, 0, 1]
  (4..number_of_windows).each { |i| temp_results.append(2 * temp_results.last + (2**(i - 4) - temp_results.shift)) }
  number_of_windows < 3 ? 0 : temp_results.last
end

new_case = ''
File.readlines(ARGV[0]).each do |line|
  if line[0] == '#'
    new_case = line.split(' ').last
  else
    p format('%<number_case>s. %<result>s', { number_case: new_case, result: summa(line.to_i) })
  end
end
