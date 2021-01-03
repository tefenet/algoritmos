#!/usr/bin/ruby
# frozen_string_literal:false

# Class to make a Stack
class Stack < Array
  def initialize
    super
    @min_elements = []
  end

  # This method is used to get minimum element of stack
  def min
    @min_elements.last
  end

  # This method is used to add an element to stack
  def push(value)
    if empty? || value < @min_elements.last
      @min_elements.push(value)
    else
      @min_elements.push(@min_elements.last)
    end
    super
  end

  # This method is used to pop top of stack
  def pop
    @min_elements.pop
    super
  end
end
