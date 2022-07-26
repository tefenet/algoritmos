#!/usr/bin/ruby
# frozen_string_literal:false

# a Class to find the first numbers in an array that sum K, an Integer number
class KSumSearcher
  def initialize(input_array)
    @the_array = input_array
    @registry = {}
    @combinations = {}
    @possible_index = []
  end

  def combinations_keys_iteration(a_number, index)
    temp_combinations = {}
    @combinations.each_key do |saved_combination|
      return @combinations[saved_combination] if a_number + saved_combination == @k_number

      if (a_number + saved_combination).abs < @k_number.abs
        temp_combinations[a_number + saved_combination] = [@combinations[saved_combination], index].flatten
      end
    end
    @combinations.merge!(temp_combinations) { |_ky, oldval, _newval| oldval }
    []
  end

  def registry_keys_iteration(a_number, index)
    @registry.each_key do |saved|
      return [@registry[saved].first] if a_number + saved == @k_number

      if (a_number + saved).abs < @k_number.abs && @combinations[a_number + saved].nil?
        @combinations[a_number + saved] = [@registry[saved].first, index]
      end
    end
    []
  end

  def no_success_with_registred
    @possible_index[1].empty? || (!@possible_index[0].empty? && @possible_index[1][0] > @possible_index[0].first)
  end

  def search_solution(index)
    return [@possible_index[0], index].flatten if no_success_with_registred

    [@possible_index[1], index].flatten
  end

  def process_number(a_number, index)
    @possible_index.append(combinations_keys_iteration(a_number, index))
    @possible_index.append(registry_keys_iteration(a_number, index))

    return search_solution(index) unless @possible_index.flatten.empty?

    @possible_index.clear
    nil
  end

  def search(k_number)
    @k_number = k_number
    @the_array.each_with_index do |a_number, index|
      return index unless @k_number - a_number != 0

      result = process_number(a_number, index)
      return result unless result.nil?

      @registry[a_number] = [index] if @registry[a_number].nil?
    end
    'no se encontro resultado'
  end
end

require 'json'
# receive an array like [1,2,3], and a number, returns the expected array of indexes
p KSumSearcher.new(JSON.parse(ARGV[0])).search(ARGV[1].to_i)
