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

  # a class that represent a chip in the board
  class Chip
    attr_reader :row, :column

    def initialize(row, column)
      @row = row
      @column = column
    end
  end

  def out_of_bounds(new_row, new_column)
    new_row >= @height || new_row.negative? || new_column.negative? || new_column >= @width
  end

  def moves(pixel, position, my_neighbours)
    new_row = pixel.row + Y_MOVE[position]
    new_column = pixel.column + X_MOVE[position]
    my_neighbours.append(Chip.new(new_row, new_column)) unless out_of_bounds(new_row, new_column)
  end

  def get_neighbours(pixel)
    my_neighbours = []
    0.upto(3) { |position| moves(pixel, position, my_neighbours) }
    my_neighbours
  end

  def are_the_same_color(pixel, other_pixel)
    @input[pixel.row][pixel.column] == @input[other_pixel.row][other_pixel.column]
  end

  def visit(pixel)
    @visited[pixel.row][pixel.column] = true
    1
  end

  def visited?(pixel)
    @visited[pixel.row][pixel.column]
  end

  def search_the_group(remaining_chips, current_pixel)
    remaining_chips.concat(
      get_neighbours(current_pixel).select do |neighbour_pixel|
        are_the_same_color(neighbour_pixel, current_pixel) and !visited?(neighbour_pixel)
      end
    )
  end

  def bfs(root_chip)
    remaining_chips = [root_chip]
    size = -1
    until remaining_chips.empty?
      current_pixel = remaining_chips.shift
      size += visit(current_pixel)
      search_the_group(remaining_chips, current_pixel)
    end
    size.positive? ? 1 : 0
  end

  # count a group when find 2 or more contiguous tiles of the same color
  # returns the number of groups
  def count_stains
    stains_count = 0
    @height.times do |row|
      @width.times do |column|
        next if @visited[row][column] || @input[row][column].zero?

        stains_count += bfs(Chip.new(row, column))
      end
    end
    stains_count
  end
end

# esto es solo una prueba
test = [[1, 2, 3, 4], [1, 0, 3, 3], [4, 0, 1, 2]]
counter = StainFinder.new(test)
p counter.count_stains
