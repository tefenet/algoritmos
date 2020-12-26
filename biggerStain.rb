#!/usr/bin/ruby
# frozen_string_literal:false

# esta clase contiene la logica para resolver el algoritmo de CCL mediante BFS
class StainFinder
  X_MOVE = [0, 0, 1, -1].freeze
  Y_MOVE = [1, -1, 0, 0].freeze

  def initialize(board)
    @input = board
    @height = board.length
    @width = board[0].length
    @visited = Array.new(@height) { Array.new(@width) { false } }
  end

  # Pixel class represent a poxel in the board
  class Pixel
    attr_reader :row, :column

    def initialize(row, column)
      @row = row
      @column = column
    end
  end

  def get_neighbours(pixel)
    my_neighbours = []
    0.upto(3) do |position|
      new_row = pixel.row + Y_MOVE[position]
      new_column = pixel.column + X_MOVE[position]
      if new_row < @height && new_row >= 0 && new_column >= 0 && new_column < @width
        my_neighbours.append(Pixel.new(new_row, new_column))
      end
    end
    my_neighbours
  end

  def are_the_same_color(pixel, other_pixel)
    @input[pixel.row][pixel.column] == @input[other_pixel.row][other_pixel.column]
  end

  def set_visited(pixel)
    @visited[pixel.row][pixel.column] = true
  end

  def is_visited(pixel)
    @visited[pixel.row][pixel.column]
  end

  def bfs(rootPixel)
    stainSize = 0
    remaining_pixels = [rootPixel]
    until remaining_pixels.empty?
      current_pixel = remaining_pixels.shift
      stainSize += 1
      get_neighbours(current_pixel).select do |neighbour_pixel|
        are_the_same_color(neighbour_pixel, current_pixel) and !is_visited(neighbour_pixel)
      end
                                   .each do |neighbour_pixel|
        set_visited(neighbour_pixel)
        remaining_pixels.append(neighbour_pixel)
      end
    end
    stainSize
  end

  # this is a bean class
  class Stain
    attr_accessor :size, :color

    def initialize
      self.size = 0
    end
  end

  def computeBiggerStain
    biggerStain = Stain.new
    @height.times do |row|
      @width.times do |column|
        next if @visited[row][column]

        @visited[row][column] = true
        stainSize = bfs(Pixel.new(row, column))
        if stainSize > biggerStain.size
          biggerStain.size = stainSize
          biggerStain.color = @input[row][column]
        end
      end
    end
    biggerStain
  end
end

def findMaxStainOnBoard(board, caseNumber)
  biggerStain = StainFinder.new(board).computeBiggerStain
  puts " #{caseNumber}. (\"#{biggerStain.color.chr}\",#{biggerStain.size})"
end

def main(pArgs)
  board = []
  new_case = 0
  File.readlines(pArgs).each do |line|
    if line[0] == '#'
      unless board.empty?
        findMaxStainOnBoard(board, new_case)
        board.clear
      end
      new_case = line.split(' ').last
    else
      board.append(line.bytes.tap(&:pop))
    end
  end
  findMaxStainOnBoard(board, new_case)
end

main(ARGV[0])
