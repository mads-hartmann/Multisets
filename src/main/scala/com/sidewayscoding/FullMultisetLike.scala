package com.sidewayscoding

/**
 * A FullMultiset is a Multiset that keeps all occurrences of the elements in memory. This is useful
 * if the equals mehtod isn't defined over all of the properties of the elements as you won't loose
 * any information.
 */
trait FullMultisetLike[A, +This <: FullMultiset[A] with FullMultisetLike[A, This]] extends MultisetLike[A, This] {

  /**
   * Returns a Map with each distinct element mapping to the sequences of elements that are
   * equal to it.
   */
  def copies: Map[A, Seq[A]]

  /**
   * Returns all the occurrences of `a`.
   */
  def get(a: A): Seq[A]

  override def removed(a: A): This = removed(a, implicitly[Equiv[A]])

  override def removedAll(a: A): This = removed(a, implicitly[Equiv[A]])

  /**
   * Removes the first element in this multiset that are equal to `a`with respect
   * to the `equals` method defined by `eq`.
   * 
   * The `equals` method defined by `eq` must be more strict than the equals method
   * defined by the element, that is, for any elements X and Y if X.equals(Y) is true
   * than eq.equals(X,Y) must also return true but not vice-versa.
   * 
   */
  def removed(a: A, eq: Equiv[A]): This

  /**
   * Removes all elements in this multiset that are equal to `a`with respect
   * to the `equals` method defined by `eq`.
   * 
   * The `equals` method defined by `eq` must be more strict than the equals method
   * defined by the element, that is, for any elements X and Y if X.equals(Y) is true
   * than eq.equals(X,Y) must also return true but not vice-versa.
   */
  def removedAll(a: A, eq: Equiv[A]): This
}