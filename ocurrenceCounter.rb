#!/usr/bin/ruby
# frozen_string_literal:true

# this class count ocurrences
class OccurrenceCounterp
  # this method count ocurrences
  def initialize(pattern_to_search)
    @text = 'aaabbccaaaaddbb'
    @pattern_to_search = pattern_to_search
  end

  def count_occurrences
    @text.scan(/(?=#{@pattern_to_search})/).count
  end
end

epe = OccurrenceCounterp.new(STDIN.gets.chomp)
puts epe.count_occurrences
