#!/usr/bin/ruby
# frozen_string_literal:true

# create a counter with a given text, then call count_ocurrences with the pattern to search
class OccurrenceCounter
  def initialize(input_text)
    @text = input_text
  end

  # count ocurrences of a pattern in a text
  # @param pattern_to_search [String]
  # @return [Numeric] the count
  def count_occurrences(pattern_to_search)
    @text.scan(/(?=#{pattern_to_search})/).count
  end
end

puts 'ingrese un patrón de texto a buscar'
pattern_to_search = $stdin.gets.chomp
p format('%<number_case>s. %<result>s',
         { number_case: new_case, result: OccurrenceCounter.new(ARGV[0]).count_occurrences(pattern_to_search) })
