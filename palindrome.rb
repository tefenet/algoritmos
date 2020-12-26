#!/usr/bin/ruby
# frozen_string_literal:true

def palindrome(num)
  if num.is_a? Numeric
    t = num.to_s
    t.reverse == t
  else
    num.reverse == num
  end
end

def get_palindrome(input)
  until palindrome(input) do input = input.to_i + 1 end
  input
end

puts 'ingrese un numero:'
puts get_palindrome(gets.chomp.to_s)
