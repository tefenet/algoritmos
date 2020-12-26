#!/usr/bin/env ruby
# frozen_string_literal:false

# speak 3 digits
class Transformer
  UNITS = ['', 'un ', 'dos ', 'tres ', 'cuatro ', 'cinco ', 'seis ', 'siete ', 'ocho ', 'nueve ', 'diez ',
           'once ', 'doce ', 'trece ', 'catorce ', 'quince ', 'dieciseis ', 'diecisiete ', 'dieciocho ',
           'diecinueve ', 'veinte '].freeze
  TENS = ['', '', 'veinti', 'treinta ', 'cuarenta ', 'cincuenta ', 'sesenta ', 'setenta ', 'ochenta ',
          'noventa ', 'cien '].freeze
  HUNDREDS = ['', 'ciento ', 'doscientos ', 'trescientos ', 'cuatrocientos ', 'quinientos ', 'seiscientos ',
              'setecientos ', 'ochocientos ', 'novecientos '].freeze

  def self.transform_last_two(tens, units)
    ten = TENS[tens]
    unit = UNITS[units]
    if tens >= 3 && !units.zero?
      format('%sy %s', ten, unit)
    else
      format('%s%s', ten, unit)
    end
  end

  def self.transform_units_and_tens(three_digits)
    last_two_digits = three_digits[1..2].to_i
    if last_two_digits <= 20
      UNITS[last_two_digits]
    else
      transform_last_two(last_two_digits / 10, last_two_digits % 10)
    end
  end

  def self.transform(three_digits)
    return 'cien ' if three_digits == '100'

    format('%<left_digit_word>s%<rigth_2digit_words>s',
           { left_digit_word: HUNDREDS[three_digits[0].to_i],
             rigth_2digit_words: transform_units_and_tens(three_digits) })
  end
end

# given a number, i speak it in spanish
class Spoken
  class << self
    def init(numeric_word = '0')
      raise ArgumentError, format('el numero %s tiene mas de 12 cifras', numeric_word) if numeric_word.length > 12

      @@input_number = numeric_word.rjust(12, '0')
      @@spoken_number = ''
    end

    def translate_millions(millions, billions)
      if millions == '001' && billions.zero?
        @@spoken_number << 'un millon '
      elsif millions.to_i.positive? || billions.positive?
        @@spoken_number << format('%smillones ', Transformer.transform(millions))
      end
    end

    def translate_thousands(thousands)
      if thousands == '001'
        @@spoken_number << 'mil '
      elsif thousands.to_i.positive?
        @@spoken_number << format('%smil ', Transformer.transform(thousands))
      end
    end

    def translate_hundreds(hundreds)
      return unless hundreds.to_i.positive?

      @@spoken_number << format('%s ', Transformer.transform(hundreds))
      @@spoken_number = @@spoken_number[0...-2] + 'o' if hundreds[-2..-1].to_i == 1
    end

    def text_number(numeric_word)
      init(numeric_word)
      translate_thousands(@@input_number[0...3])
      translate_millions(@@input_number[3...6], @@input_number[0...3].to_i)
      translate_thousands(@@input_number[6...9])
      translate_hundreds(@@input_number[9...12])
      @@spoken_number.empty? ? 'cero' : @@spoken_number.strip
    end
  end
end

new_case = ''

File.readlines(ARGV[0]).each do |line|
  if line[0] == '#'
    new_case = line.split(' ').last
  else
    p format('%<number_case>s. %<result>s', { number_case: new_case, result: Spoken.text_number(line.strip) })
  end
end
